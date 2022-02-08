package tyrannibook.data.element;

import tyrannibook.repository.BookRepository;

public interface IDataElement {
	void load(BookRepository source);
}
