package com.xilinxlite.communication;

import java.util.ArrayList;
import java.util.List;

public class SimulationData {

	public class SimulationDatum {
		private int time;
		private String varName;
		private int varValue;

		SimulationDatum(int time, String varName, int varValue) {
			this.time = time;
			this.varName = varName;
			this.varValue = varValue;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public String getVarName() {
			return varName;
		}

		public void setVarName(String varName) {
			this.varName = varName;
		}

		public int getVarValue() {
			return varValue;
		}

		public void setVarValue(int varValue) {
			this.varValue = varValue;
		}

	}
	
	List<SimulationDatum> main_list;
	
	public SimulationData() {
		main_list = new ArrayList<>();
	}
	
	

}
