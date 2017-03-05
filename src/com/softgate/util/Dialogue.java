package com.softgate.util;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import com.softgate.App;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;

public final class Dialogue {
	
	private Dialogue() {
		
	}

	public static void openDirectory(String headerText, File dir) {

		OptionMessage alert = new OptionMessage(headerText);

		ButtonType choiceOne = new ButtonType("Yes.");
		ButtonType close = new ButtonType("No", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(choiceOne, close);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {

			ButtonType type = result.get();

			if (type == choiceOne) {
				try {
					Desktop.getDesktop().open(dir);
				} catch (Exception ex) {
					Dialogue.showException("Error while trying to view image on desktop.", ex);
				}
			}

		}
	}
	
	public static File chooseFile() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return chooser.showOpenDialog(App.getStage());
	}
	
	public static File chooseFile(String title) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(title);
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return chooser.showOpenDialog(App.getStage());
	}
	
	public static File chooseDirectory() {
	    DirectoryChooser chooser = new DirectoryChooser();
	    chooser.setInitialDirectory(new File(System.getProperty("user.home")));
	    return chooser.showDialog(App.getStage());
	}
	
	public static List<File> chooseFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return fileChooser.showOpenMultipleDialog(App.getStage());
	}
	
	public static WarningMessage showWarning(String message) {
		return new WarningMessage(message);
	}

	public static InformationMessage showInfo(String title, String message) {
		return new InformationMessage(title, message);
	}

	public static ExceptionMessage showException(String message, Exception ex) {
		return new ExceptionMessage(message, ex);
	}
	
	public static InputMessage showInput(String title, String context, String text) {
		return new InputMessage(title, context, text);
	}
	
	public static InputMessage showInput(String context, String text) {
		return new InputMessage("Input", context, text);
	}
	
	public static final class WarningMessage extends Alert {

		public WarningMessage(String message) {
			super(AlertType.WARNING);
			setTitle("Warning");
			setHeaderText(null);
			setContentText(message);
			
			showAndWait();
		}

	}

	public static final class OptionMessage extends Alert {

		/**
		 * Creates a new {@link OptionDialogue}.
		 * 
		 * @param header
		 * 		The text to display
		 */
		public OptionMessage(String header) {
			super(AlertType.CONFIRMATION);
			setTitle("Information");
			setHeaderText(header);
			getButtonTypes().clear();
		}

	}
	
	public static final class InputMessage extends TextInputDialog {
		
		public InputMessage(String title, String context, String text) {
			super(text);
			this.setTitle(title);
			this.setHeaderText(null);
			this.setContentText(context);
		}

	}

	public static final class InformationMessage extends Alert {
	      
	      public InformationMessage(String title, String content) {
	            this(title, null, content);
	      }

	      public InformationMessage(String title, String header, String content) {
	            super(AlertType.INFORMATION);
	            setTitle(title);
	            setHeaderText(header);
	            setContentText(content);
	            showAndWait();
	      }

	}

	public static final class ExceptionMessage extends Alert {

	      public ExceptionMessage(String message, Exception ex) {
	            super(AlertType.ERROR);
	            setTitle("Exception");
	            setHeaderText("Encountered an Exception");
	            setContentText(message);

	            StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            ex.printStackTrace(pw);
	            String exceptionText = sw.toString();

	            Label label = new Label("The exception stacktrace was:");

	            TextArea textArea = new TextArea(exceptionText);
	            textArea.setEditable(false);
	            textArea.setWrapText(true);

	            textArea.setMaxWidth(Double.MAX_VALUE);
	            textArea.setMaxHeight(Double.MAX_VALUE);
	            GridPane.setVgrow(textArea, Priority.ALWAYS);
	            GridPane.setHgrow(textArea, Priority.ALWAYS);

	            GridPane expContent = new GridPane();
	            expContent.setMaxWidth(Double.MAX_VALUE);
	            expContent.add(label, 0, 0);
	            expContent.add(textArea, 0, 1);

	            getDialogPane().setExpandableContent(expContent);

	            showAndWait();
	      }

	}
	
}
