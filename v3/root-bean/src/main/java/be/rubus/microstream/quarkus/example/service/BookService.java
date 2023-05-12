package be.rubus.microstream.quarkus.example.service;

import be.rubus.microstream.quarkus.example.database.Root;
import be.rubus.microstream.quarkus.example.model.Book;
import one.microstream.storage.types.StorageManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService extends AbstractService {

    public List<Book> getAll() {
        return root.getBooks();
    }

    public Optional<Book> getBookByISBN(String isbn) {
        return root.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findAny();
    }

}
