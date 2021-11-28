package client.commands;

import client.model.Book;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner;
    private BookCommand command;

    public InputReader(){
        scanner = new Scanner(System.in);
        command = new BookCommand();
    }

    public boolean readCommand(){
        System.out.print("> ");
        String commandString = scanner.nextLine();
        switch (commandString){
            case "add":
                command.addBook(readBookProperties());
                break;
            case "find":
                readSearchParams();
                break;
            case "help":
                printAvailableCommands();
                break;
            case "exit":
                return false;
            default:
                System.out.println("Command '" + commandString + "' doesn't exist. Use 'help' for the list of available commands.");
        }
        return true;
    }

    public void readSearchParams(){
        System.out.println("Choose method of search: \n" +
                "1: Search by book's name.\n" +
                "2: Search by author's name.\n" +
                "3: Search by using key words. \n" +
                "4: Cancel search.");
        boolean exitFlag = false;
        while(!exitFlag) {
            int method = 0;
            try {
                method = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("No such option.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
            switch (method) {
                case 1:
                    System.out.println("Enter book's name: ");
                    String name = scanner.nextLine();
                    command.findBookByParam("name", name);
                    exitFlag = true;
                    break;
                case 2:
                    System.out.println("Enter author's name: ");
                    String authorName = scanner.nextLine();
                    command.findBookByParam("author", authorName);
                    exitFlag = true;
                    break;
                case 3:
                    System.out.println("Enter key words separated by space. Example: 'keyword1 keyword2 keyword3'.");
                    String keywords = scanner.nextLine();
                    command.findBookByParam("keywords", keywords);
                    exitFlag = true;
                    break;
                case 4:
                    exitFlag = true;
                    break;
                default:
                    System.out.println("No such option.");
            }
        }
    }

    public Book readBookProperties(){
        System.out.println("Enter book's name:");
        String name = scanner.nextLine();

        System.out.println("Enter author's name:");
        String authorName = scanner.nextLine();

        System.out.println("Enter book's genre:");
        String genre = scanner.nextLine();

        System.out.println("Enter book's publish date (format: YYYY-MM-DD):");
        String publishDate = scanner.nextLine();

        System.out.println("Enter book's annotation:");
        String annotation = scanner.nextLine();

        System.out.println("Enter book's ISBN:");
        String isbn = scanner.nextLine();

        return new Book(name, authorName, genre, publishDate, annotation, isbn);
    }

    public void printAvailableCommands(){
        System.out.println("add  - Add new book to the library. \n" +
                "find - Search for the book from the library. \n" +
                "exit - Exit the program. \n" +
                "help - Bring up this list of commands.");
    }
}
