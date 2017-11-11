package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {
    private int caretPosition = 0;

    private StringBuilder str = new StringBuilder("");

    @FXML
    Button Check;

    @FXML
    Button ClearButton;

    @FXML
    Button leftQuote;

    @FXML
    Button rightQuote;

    @FXML
    Button HelpButton;

    @FXML
    TextField textField;

    @FXML
    Label answer;

    @FXML
    void onCheckClicked(){
        if(textField.getText().length()!=0) {
            try {
                Analyzer.Expression(textField.getText() + "\n", 0);

                answer.setTextFill(Color.web("#0F0"));
                answer.setText("Выражение верно");
            } catch (GrammarException exc) {
                answer.setTextFill(Color.web("#F00"));
                answer.setText(exc.getMessage());

                caretPosition = exc.getSymbolPosiion();
                textField.requestFocus();
                textField.selectPositionCaret(caretPosition);
            }
        }

    }

    @FXML
    void caretChanged(){
        caretPosition = textField.getCaretPosition();
    }

    @FXML
    void onHelpButtonClicked(){
       if (Main.helpStage.isShowing())
           Main.helpStage.toFront();
       else
            Main.helpStage.show();
    }

    @FXML
    void typeLeftQuote(){
        str.setLength(0);
        str.append(textField.getText(0, caretPosition));
        str.append("«");
        str.append(textField.getText(caretPosition, textField.getText().length()));

        textField.setText(str.toString());
        textField.requestFocus();
        textField.positionCaret(++caretPosition);
    }

    @FXML
    void typeRightQuote(){
        str.setLength(0);
        str.append(textField.getText(0, caretPosition));
        str.append("»");
        str.append(textField.getText(caretPosition, textField.getText().length()));

        textField.setText(str.toString());
        textField.requestFocus();
        textField.positionCaret(++caretPosition);
    }

    @FXML
    void clearTextField(){
        textField.clear();
    }
}
