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
	private TreeItem<Object> modulesForTop = new TreeItem<>("Modules");

	private MessageViewMgr messageView = null;

	/**
	 * Constructor.
	 */
	public DirectoryViewMgr() {
		this.fnPack = FunctionPack.getInstance();
	}

	/**
	 * Sets MessageViewMgr reference. Also sets listener to treeView.
	 * 
	 * @param mgr
	 */
	public void setMessageViewMgr(MessageViewMgr mgr) {
		this.messageView = mgr;

		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue,
					TreeItem<Object> newValue) {
				if (newValue != null) {
					if (newValue.getValue() instanceof File) {
						messageView.viewFile((File) newValue.getValue());
					}
				} else {
					messageView.clearFileView();
				}
			}

		});
	}

	/**
	 * Gets currently selected module's name. If current selection is not a module,
	 * returns null instead.
	 * 
	 * @return module's name; null if otherwise
	 */
	public String getSelectedModuleName() {
		TreeItem<Object> treeItem = treeView.getSelectionModel().getSelectedItem();
		if (treeItem.getParent().equals(modulesForTop)) {
			String name = (String) treeItem.getValue();
			if (name.endsWith("*")) {
				name = name.substring(0, name.length() - 1);
			}
			return name.substring(1);
		}
		return null;
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

		if (!modulesForTop.getChildren().isEmpty()) {
			root.getChildren().add(modulesForTop);
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

	private void updateModules() {

		String top = fnPack.getTopModule();
		List<String> modules = fnPack.getTopModules();
		modulesForTop.getChildren().clear();

		// error if either is empty
		if (top.isEmpty() || modules.isEmpty()) {
			logger.log(Level.WARNING, "Error with modules detected");
		}

		// error if modules does not contain top when it should
		else if (!modules.contains(top)) {
			System.out.println("top : " + top);
			for (String s : modules) {
				System.out.println("s : " + s);
			}
			logger.log(Level.WARNING, "Error with top", new Exception());
		}

		else {
			for (String module : modules) {
				modulesForTop.getChildren().add(new TreeItem<>(module + (module.equals(top) ? "*" : "")));
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

	@Override
	public void update() {

		// Update each category
		updateVerilogFiles();
		updateSynthesisFiles();
		updateModules();

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
				if (newValue != null) {
					removeFile.setDisable(!(newValue.getValue() instanceof File));
					topModuleBtn.setDisable(!newValue.getParent().equals(modulesForTop));
				}
			}

		});
	}

	@Override
	protected void addFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select file...");
		fc.setInitialDirectory(new File(fnPack.getWorkingDirectory()));
		fc.getExtensionFilters().add(new ExtensionFilter("Verilog file", "*.v"));
		List<File> files = fc.showOpenMultipleDialog(new Stage());

		if (files != null) {
			for (File file : files) {
				if (!addVerilogFile(file)) {
					logger.log(Level.WARNING, "Fail to add file: " + file.getName());
					// Consider adding error handling for each failed file
				}
			}
			updateVerilogFiles();
			updateModules();
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
				updateModules();
				messageView.clearFileView();
			} else if (item.getParent() == synthesisFiles) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Delete file from disk?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					File file = (File) item.getValue();
					if (file.delete()) {
						synthesisFiles.getChildren().remove(item);
						messageView.clearFileView();
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

	@Override
	protected void setTopModule() {
		// Get reference to selected item
		TreeItem<Object> selectedItem = treeView.getSelectionModel().getSelectedItem();

		// test if parent is variable 'modulesForTop'
		if (selectedItem.getParent().equals(modulesForTop)) {
			// expects String for selected item
			// error if not String
			if (!(selectedItem.getValue() instanceof String)) {
				logger.log(Level.SEVERE, "Non-String found in topModules.", new Exception());
				return;
			}

			String item = (String) selectedItem.getValue();

			// runs if selected item is not top module
			if (!item.endsWith("*")) {
				logger.log(Level.INFO, "setting \"" + item + "\" as top module.");
				fnPack.setTopModule(item);
				updateModules();
			}
		}
	}

}
