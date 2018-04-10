package com.xilinxlite.gui.functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerilogTestFixtureGenerator {

	private static Logger logger = Logger.getLogger(VerilogTestFixtureGenerator.class.getName());

	public static File parse(File source_file, String moduleName) {
		if (!source_file.exists()) {
			System.err.println("File does not exist.");
			return null;
		}
		
		logger.log(Level.INFO, "Parsing for " + moduleName);

		try {
			String content = read(source_file);

			String module = "";
			List<String> arg = new ArrayList<>();
			List<String[]> inputs = new ArrayList<>();
			List<String[]> outputs = new ArrayList<>();

			// remove comments
			Pattern p = Pattern.compile("/\\*.*\\*/", Pattern.DOTALL);
			Matcher m = p.matcher(content);
			while (m.find()) {
				content = content.replaceAll(m.group(), "");
			}
			p = Pattern.compile("//[^\\n]*", Pattern.DOTALL);
			m = p.matcher(content);
			while (m.find()) {
				content = content.replaceAll(m.group(), "");
			}

			// module
			p = Pattern.compile("module +(?<name>\\w+)[^;]*;", Pattern.DOTALL);
			m = p.matcher(content);
			m.find();
			module = m.group("name");
			p = Pattern.compile("\\([^\\)]+\\);", Pattern.DOTALL);
			m = p.matcher(m.group());
			m.find();
			Pattern p1 = Pattern.compile("\\b\\w+\\b", Pattern.DOTALL);
			Matcher m1 = p1.matcher(m.group());
			while (m1.find()) {
				arg.add(m1.group());
			}

			// parameter
			p = Pattern.compile("parameter +(?<name>\\w+) += +(?<value>\\d+);", Pattern.DOTALL);
			m = p.matcher(content);
			while (m.find()) {
				content = content.replaceAll(m.group("name"), m.group("value"));
			}

			// input
			p = Pattern.compile("input +(?<bus>\\[[^\\]]+\\] +)?(?<variables>[^;]+);", Pattern.DOTALL);
			m = p.matcher(content);
			Pattern p2 = Pattern.compile("\\[(?<first>\\d+)(?<arith>-1)?:0\\]", Pattern.DOTALL);
			Matcher m2;
			while (m.find()) {
				String size = "1";
				m2 = p2.matcher(m.group().replaceAll(" ", ""));
				if (m2.find()) {
					size = m2.group("first");
					if (m2.group("arith") != null) {
						size = String.valueOf(Integer.valueOf(size) - 1);
					}
				}
				m1 = p1.matcher(m.group("variables"));
				while (m1.find()) {
					String[] input = { m1.group(), size };
					inputs.add(input);
				}
			}

			// output
			p = Pattern.compile("output +(?<bus>\\[[^\\]]+\\] +)?(?<variables>[^;]+);", Pattern.DOTALL);
			m = p.matcher(content);
			while (m.find()) {
				String size = "1";
				m2 = p2.matcher(m.group().replaceAll(" ", ""));
				if (m2.find()) {
					size = m2.group("first");
					if (m2.group("arith") != null) {
						size = String.valueOf(Integer.valueOf(size) - 1);
					}
				}
				m1 = p1.matcher(m.group("variables"));
				while (m1.find()) {
					String[] input = { m1.group(), size };
					outputs.add(input);
				}
			}

			File outputFile = new File(source_file.getParent() + File.separator + moduleName + ".v");
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			writer.write("`timescale 1ns / 1ps\n");
			writer.newLine();
			writer.write("module " + moduleName + ";\n");
			writer.newLine();
			writer.write("\t// Inputs\n");
			for (String[] input : inputs) {
				writer.write("\treg ");
				if (!input[1].equals("1")) {
					writer.write("[" + input[1] + ":0] ");
				}
				writer.write(input[0] + ";\n");
			}
			writer.newLine();
			writer.write("\t// Outputs\n");
			for (String[] output : outputs) {
				writer.write("\twire ");
				if (!output[1].equals("1")) {
					writer.write("[" + output[1] + ":0] ");
				}
				writer.write(output[0] + ";\n");
			}
			writer.newLine();
			writer.write("\t// Instantiate the Unit Under Test (UUT)\n");
			writer.write("\t" + module + " uut (\n");
			for (int i = 0; i < arg.size(); i++) {
				writer.write("\t\t." + arg.get(i) + "(" + arg.get(i) + ")" + (i < arg.size() - 1 ? "," : "") + "\n");
			}
			writer.write("\t);\n");
			writer.newLine();
			writer.write("\tinitial begin\n");
			writer.write("\t\t// Initialize Inputs\n");
			for (String[] input : inputs) {
				writer.write("\t\t" + input[0] + " = 0;\n");
			}
			writer.newLine();
			writer.write("\t\t// Wait 100 ns for global reset to finish\n");
			writer.write("\t\t#100;\n");
			writer.newLine();
			writer.write("\t\t// Add stimulus here\n");
			writer.newLine();
			writer.newLine();
			writer.write("\tend\n");
			writer.newLine();
			writer.write("endmodule\n");
			writer.newLine();

			writer.close();
			
			logger.log(Level.INFO, "Finished generating test fixture");
			
			return outputFile;
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error generating Verilog test fixture.", e);
			return null;
		}
	}

	private static String read(File file) {
		StringBuilder strBuilder = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				strBuilder.append(line.matches("\\*\\n") ? line : line + "\n");
			}
			reader.close();
			return strBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}