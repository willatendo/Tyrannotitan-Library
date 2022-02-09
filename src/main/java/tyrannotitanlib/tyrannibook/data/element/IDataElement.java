package tyrannotitanlib.tyrannibook.data.element;

import tyrannotitanlib.tyrannibook.repository.BookRepository;

public interface IDataElement {
	void load(BookRepository source);
}
