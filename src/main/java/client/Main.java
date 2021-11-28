package client;

import client.commands.InputReader;

public class Main {

    public static void main(String[] args){

        InputReader inputReader = new InputReader();


        System.out.println("Greetings! This is a simple library program. You have following commands available for use:");
        inputReader.printAvailableCommands();

        while(inputReader.readCommand()){}
    }
}
