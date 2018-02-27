package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.gui.DirectoryViewDesign;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Function implementation of DirectoryViewDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class DirectoryViewMgr extends DirectoryViewDesign implements Updateable {

	private static Logger logger = Logger.getLogger(DirectoryViewMgr.class.getName());

	private FunctionPack fnPack = null;

	private TreeItem<Object> verilogFiles = new TreeItem<>("Verilog files");
	private TreeItem<Object> synthesisFiles = new TreeItem<>("Synthesis files");

	private MessageViewMgr messageView = null;

	/**
	 * Constructor.
	 */
	public DirectoryViewMgr() {
		this.fnPack = FunctionPack.getInstance();
	}

	/**
	 * Sets MessageViewMgr reference. Also sets listener to treeView.
	 * @param mgr
	 */
	public void setMessageViewMgr(MessageViewMgr mgr) {
		this.messageView = mgr;

		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue,
					TreeItem<Object> newValue) {
				if (newValue.getValue() instanceof File) {
					messageView.viewFile((File) newValue.getValue());
				}
			}

		});
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

		if (fnPack == null) {
			logger.log(Level.SEVERE, "FunctionPack is missing", new Exception());
			return;
		}

		// Get list of verilog files in project
		List<String> verilogList = fnPack.getVerilogFiles();

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
		File[] wdFiles = new File(fnPack.getWorkingDirectory()).listFiles();

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
		if (fnPack.addFile(file.getAbsolutePath())) {
			verilogFiles.getChildren().add(new TreeItem<>(treeItemFile(file)));
			return true;
		} else
			return false;
	}

	private boolean removeVerilogFile(File file) {
		return fnPack.removeFile(file.getAbsolutePath());
	}

	private void openFileRead(File file) {
		if (messageView != null) {
			messageView.viewFile(file);
		}
	}

	@Override
	public void update() {

		// Update each category
		updateVerilogFiles();
		updateSynthesisFiles();

		// Activate/deactivate buttons
		boolean projectClosed = fnPack.isProjectClosed();
		addFile.setDisable(projectClosed);
		removeFile.setDisable(projectClosed);

	}

	@Override
	protected void initialize() {
		update();
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue,
					TreeItem<Object> newValue) {
				removeFile.setDisable(!(newValue.getValue() instanceof File));
			}
			
		});
	}

	@Override
	protected void addFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select file...");
		fc.setInitialDirectory(new File(fnPack.getWorkingDirectory()));
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
