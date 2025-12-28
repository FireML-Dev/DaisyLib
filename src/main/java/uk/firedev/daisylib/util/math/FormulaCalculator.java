package uk.firedev.daisylib.util.math;

import org.jetbrains.annotations.NotNull;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import uk.firedev.daisylib.util.Utils;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class FormulaCalculator {

    private static ScriptEngine scriptEngine;

    public static ScriptEngine getScriptEngine() {
        if (scriptEngine == null) {
            NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
            scriptEngine = factory.getScriptEngine();
        }
        return scriptEngine;
    }

    /**
     * Calculates the provided formula
     * @param formula The formula to calculate
     * @return The value of this formula
     * @throws FormulaException If the provided formula is invalid or an error occurs
     */
    public static double calculate(@NotNull String formula) throws FormulaException {
        try {
            Object obj = getScriptEngine().eval(formula);
            Double value = Utils.getDouble(obj.toString());
            if (value == null) {
                throw new FormulaException("Return value is not a number!");
            }
            return value;
        } catch (ScriptException exception) {
            throw new FormulaException(exception);
        }
    }

}
