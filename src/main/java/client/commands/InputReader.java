package client.commands;

import client.model.Book;
import client.validation.BookValidator;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner;
    private BookCommand command;
    private String login;
    private String password;
    private Boolean isAuthorized = false;

    public InputReader(){
        scanner = new Scanner(System.in);
        command = new BookCommand();
    }


    public boolean readAuthorizationCommand(){
        System.out.println("Please log in or register in order to use the application. \n" +
                "Use command 'login' or 'register'");
        System.out.print("> ");
        String commandString = scanner.nextLine();
        switch (commandString){
            case "login":
                readLoginInfo();
                break;
            case "register":
                readRegistrationInfo();
                break;
            case "exit":
                return false;
            default:
                System.out.println("Please only use 'login' or 'register' commands.");
        }
        return true;
    }

    public void readLoginInfo(){
        System.out.println("Enter your user name:");
        String login = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        AuthorizationCommand authCommand = new AuthorizationCommand();
        if (authCommand.loginUser(login, password)) {
            System.out.println("You successfully logged in!");
            this.login = login;
            this.password = password;
            isAuthorized = true;
        }
    }

    public void readRegistrationInfo(){
        System.out.println("Enter your user name:");
        String login = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        AuthorizationCommand authCommand = new AuthorizationCommand();
        if (authCommand.registerUser(login, password)) {
            System.out.println("Registered new user: " + login);
            this.login = login;
            this.password = password;
            isAuthorized = true;
        }
    }

    public boolean readCommand(){
        if (!isAuthorized)
            return readAuthorizationCommand();
        System.out.print("> ");
        String commandString = scanner.nextLine();
        switch (commandString){
            case "add":
                command.addBook(readBookProperties(), login, password);
                break;
            case "find":
                readSearchParams();
                break;
            case "help":
                printAvailableCommands();
                break;
            case "logout":
                isAuthorized = false;
                login = null;
                password = null;
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
                    command.findBookByParam("name", name, login, password);
                    exitFlag = true;
                    break;
                case 2:
                    System.out.println("Enter author's name: ");
                    String authorName = scanner.nextLine();
                    command.findBookByParam("author", authorName, login, password);
                    exitFlag = true;
                    break;
                case 3:
                    System.out.println("Enter key words separated by space. Example: 'keyword1 keyword2 keyword3'.");
                    String keywords = scanner.nextLine();
                    command.findBookByParam("keywords", keywords, login, password);
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

        BookValidator validator = new BookValidator();
        Book book = new Book(name, authorName, genre, publishDate, annotation, isbn, login);
        validator.validateBook(book);
        return book;
    }

    public void printAvailableCommands(){
        System.out.println("add  - Add new book to the library. \n" +
                "find - Search for the book from the library. \n" +
                "exit - Exit the program. \n" +
                "help - Bring up this list of commands.");
    }
}
