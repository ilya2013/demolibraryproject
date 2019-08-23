delete from author;
delete from book;

insert into author (id, full_name) values (1, 'test2');
insert into book (id, title, language, author_id) values (1, 'Live','rus',  1);