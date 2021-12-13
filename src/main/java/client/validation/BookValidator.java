package client.validation;

import client.model.Book;

public class BookValidator {

    public BookValidator(){}

    public void validateBook(Book book){
        try{
            checkForEmptyString(book.getName(), "Name");
            checkForEmptyString(book.getAuthorName(), "Author name");
            checkForEmptyString(book.getGenre(), "Genre");
            checkForEmptyString(book.getPublishDate(), "Publish date");
            checkForEmptyString(book.getIsbn(), "ISBN");
        }
        catch (IllegalBookArgumentsException e){
            System.out.println(e.getMessage());
        }
    }

    public void checkForEmptyString(String value, String field) throws  IllegalBookArgumentsException{
        if (value == null || value.isEmpty()){
            throw new IllegalBookArgumentsException(field + " can't be empty.");
        }
    }
}
