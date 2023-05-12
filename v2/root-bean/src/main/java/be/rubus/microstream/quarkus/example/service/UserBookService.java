package be.rubus.microstream.quarkus.example.service;

import be.rubus.microstream.quarkus.example.exception.BookAlreadyAssignedException;
import be.rubus.microstream.quarkus.example.exception.BookNotFoundException;
import be.rubus.microstream.quarkus.example.exception.UserNotFoundException;
import be.rubus.microstream.quarkus.example.model.Book;
import be.rubus.microstream.quarkus.example.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UserBookService extends AbstractService {

    private static final Object USER_BOOK_LOCK = new Object();

    @Inject
    UserService userService;

    @Inject
    BookService bookService;

    public void addBookToUser(String id, String isbn) {
        synchronized (USER_BOOK_LOCK) {
            Optional<User> byId = userService.getById(id);
            if (byId.isEmpty()) {
                throw new UserNotFoundException();
            }
            Optional<Book> bookByISBN = bookService.getBookByISBN(isbn);
            if (bookByISBN.isEmpty()) {
                throw new BookNotFoundException();
            }

            User user = byId.get();
            Book book = bookByISBN.get();
            if (user.getBooks().contains(book)) {
                throw new BookAlreadyAssignedException();
            }
            root.addBookToUser(user, book);
        }
    }
}
