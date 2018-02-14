package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.DirectoryViewDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DirectoryViewMgr extends DirectoryViewDesign implements Updateable {

	private static Logger logger = Logger.getLogger(DirectoryViewMgr.class.getName());

	private CommunicationMgr cmd = null;

	private TreeItem<Object> verilogFiles = new TreeItem<>("Verilog files");
	private TreeItem<Object> synthesisFiles = new TreeItem<>("Synthesis files");

	public DirectoryViewMgr(CommunicationMgr cmd) {
		this.cmd = cmd;
	}

	@Override
	public void update() {

		// Update each category
		updateVerilogFiles();
		updateSynthesisFiles();

		// Activate/deactivate buttons
		boolean projectOpen = !cmd.getProjectName().isEmpty();
		addFile.setDisable(!projectOpen);
		removeFile.setDisable(!projectOpen);

	}

	@Override
	protected void initialize() {
		update();
	}

	private void updateList() {
		// Clear list
		root.getChildren().clear();

		// Add non-empty lists to root
		if (!verilogFiles.getChildren().isEmpty()) {
			root.getChildren().add(verilogFiles);
		}

		if (!synthesisFiles.getChildren().isEmpty()) {
			root.getChildren().add(synthesisFiles);
		}
	}

	private void updateVerilogFiles() {

		if (cmd == null) {
			logger.log(Level.SEVERE, "Attempted update with no CommunicationMgr", new Exception());
			return;
		}

		// Get list of verilog files in project
		List<String> verilogList = cmd.getFiles();

		// Update verilog list
		verilogFiles.getChildren().clear();
		for (String s : verilogList) {
			File file = treeItemFile(s);

			if (file.exists() && file.isFile() && file.getAbsolutePath().endsWith(".v")) {
				verilogFiles.getChildren().add(new TreeItem<>(file));
			}
		}

		updateList();

	}

	private void updateSynthesisFiles() {

		// Get list of files found in working directory
		File[] wdFiles = new File(cmd.getWorkingDirectory()).listFiles();

		// Clear tree list
		synthesisFiles.getChildren().clear();

		// Add matching files to list
		for (File f : wdFiles) {
			String s = f.getAbsolutePath();
			if (s.endsWith(".syr")) {
				synthesisFiles.getChildren().add(new TreeItem<>(treeItemFile(s)));
			}
		}

		updateList();

	}

	private File treeItemFile(String filepath) {
		return treeItemFile(new File(filepath));
	}

	private File treeItemFile(File file) {
		return new File(file.getAbsolutePath()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String toString() {
				return getAbsolutePath().replace(getParent(), "").substring(1);
			}

		};
	}

	private boolean addVerilogFile(File file) {
		if (cmd.addFile(file.getAbsolutePath())) {
			verilogFiles.getChildren().add(new TreeItem<>(treeItemFile(file)));
			return true;
		} else
			return false;
	}

	private boolean removeVerilogFile(File file) {
		return cmd.removeFile(file.getAbsolutePath());
	}

	@Override
	protected void addFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select file...");
		fc.setInitialDirectory(new File(cmd.getWorkingDirectory()));
		fc.setSelectedExtensionFilter(new ExtensionFilter("Verilog file", "*.v"));
		File file = fc.showOpenDialog(new Stage());

		if (file != null) {
			addVerilogFile(file);
			updateVerilogFiles();
		}
	}

	@Override
	protected void removeFile() {
		MultipleSelectionModel<TreeItem<Object>> tvSelModel = treeView.getSelectionModel();
		TreeItem<Object> item = tvSelModel.getSelectedItem();
		if (item.getValue() instanceof File) {
			if (item.getParent() == verilogFiles) {
				removeVerilogFile((File) item.getValue());
				verilogFiles.getChildren().remove(item);
				updateVerilogFiles();
			} else if (item.getParent() == synthesisFiles) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Delete file from disk?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					File file = (File) item.getValue();
					if (file.delete()) {
						synthesisFiles.getChildren().remove(item);
					}
				}
				updateSynthesisFiles();
			}
		}
	}

	@Override
	protected void refresh() {
		update();
	}

}
