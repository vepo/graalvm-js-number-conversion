package dev.vepo.graalvmjs.numbers;

import java.security.InvalidParameterException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

public class NumberConversionError {

  public static void expectLong(Object shouldBeLong) {
    System.out.println(shouldBeLong.getClass());
    if (!(shouldBeLong instanceof Long)) {
      throw new InvalidParameterException("This is not a long value!");
    }
  }

  public static void main(String[] args) throws ScriptException {
    ScriptEngine engine = GraalJSScriptEngine.create(null, Context.newBuilder("js")
                                                                  .allowHostAccess(HostAccess.ALL)
                                                                  .allowHostClassLookup(s -> true)
                                                                  .allowExperimentalOptions(true)
                                                                  .out(System.out)
                                                                  .err(System.err)
                                                                  .option("js.nashorn-compat", "true"));
    engine.eval("""
                    var NumberConversionError = Java.type("dev.vepo.graalvmjs.numbers.NumberConversionError");
                    var Long = Java.type("java.lang.Long");
                    print(NumberConversionError);
                    NumberConversionError.expectLong(Long.valueOf("2147483648"));
                    NumberConversionError.expectLong(Long.valueOf("5"));
                """);
  }
}