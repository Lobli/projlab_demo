package projlab;

import java.util.ArrayList;
import java.util.Arrays;

public class SkeletonHelper {

    static ArrayList<Scenario> scenarios = new ArrayList<>();

    static int indent;

    static void call(String caller, String arg , String functionName){
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }

        System.out.println(
                String.format("%s.%s(%s)", caller, functionName, arg));
        indent += 4;
    }

    static void call(String caller, String[] args , String functionName){
        String argString = Arrays.toString(args);

        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }

        System.out.print(
                String.format("%s.%s(%s)\n",
                        caller,
                        functionName,
                        argString.substring(1, argString.length()-1)));
        indent += 4;
    }

    /*
        Azért returnF, mert a return kulcsszó Java-ban (azaz nem lehet függvény/osztály/változó neve)
     */
    static void returnF(){
        if (0 < indent)
            indent -= 4;
    }

    static boolean decide(String message){
        System.out.print(message);
        System.out.print(" ([Y]/n))\n");
        String input = System.console().readLine();
        if (input.isEmpty() || input == "y" || input == "Y")
            return true;
        return false;
    }

    static void addScenario(Scenario sc){
        scenarios.add(sc);
    }

    static void chooseScenario(){
        //while(true) {
            for (int i = 0; i < scenarios.size(); i++) {
                String out = String.format("[%s]: %s", i, scenarios.get(i).name);
                System.out.println(out);
            }

            int choice = 1; //Integer.parseInt(System.console().readLine());

            scenarios.get(choice).run();
        //}
    }
}
