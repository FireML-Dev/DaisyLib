package uk.firedev.daisylib.builders.dialog;

public class DialogBuilder {

    private DialogBuilder() {}

    public static InformationDialogBuilder information() {
        return new InformationDialogBuilder();
    }

}
