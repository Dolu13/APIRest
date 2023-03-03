drop table if exists ingredients, composition, pizza, users, commandes, consommation;

CREATE TABLE ingredients (
    id integer NOT NULL,
    name text NOT NULL,
    price double precision NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE pizza (
    id integer NOT NULL,
    name text NOT NULL,
    pastatype text NOT NULL,
    prixbase double precision NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE composition (
    idpizza integer NOT NULL,
    idingredient integer NOT NULL,
    PRIMARY KEY(idpizza,idingredient),
    CONSTRAINT fk_idingredients FOREIGN KEY (idingredient) REFERENCES ingredients(id) ON DELETE CASCADE,
    CONSTRAINT fk_idpizza FOREIGN KEY (idpizza) REFERENCES pizza(id) ON DELETE CASCADE

);

CREATE TABLE users (
    user_id integer NOT NULL,
    login text NOT NULL,
    pwd text NOT NULL,
    PRIMARY KEY(user_id)
);

create table commandes(
commande_id integer not null,
user_id integer not null,
datecommande date not null,
PRIMARY KEY(commande_id),
CONSTRAINT fk_userid FOREIGN KEY (user_id) REFERENCES users(user_id)
);


create table consommation(
idcommande integer,
idpizza integer,
    PRIMARY KEY(idcommande,idpizza),
    CONSTRAINT fk_idcommande FOREIGN KEY (idcommande) REFERENCES commandes(commande_id) ON DELETE CASCADE,
    CONSTRAINT fk_idpizza FOREIGN KEY (idpizza) REFERENCES pizza(id) ON DELETE CASCADE
);


INSERT INTO ingredients(id,name,price) VALUES (1,'pomme de terre',1.99);
INSERT INTO ingredients(id,name,price) VALUES (2,'poivrons',1.99);
INSERT INTO ingredients(id,name,price) VALUES (3,'chorizo',1.99);
INSERT INTO ingredients(id,name,price) VALUES (4,'lardons',1.99);
INSERT INTO ingredients(id,name,price) VALUES (5,'aubergines',1.99);
INSERT INTO ingredients(id,name,price) VALUES (6,'champignons',1.99);
INSERT INTO ingredients(id,name,price) VALUES (7,'reblochon',1.99);
INSERT INTO ingredients(id,name,price) VALUES (9,'basilic',1.99);
INSERT INTO ingredients(id,name,price) VALUES (10,'thon',1.99);


INSERT INTO pizza(id,name,pastatype,prixbase) VALUES (1,'margherita','tomato',7);
INSERT INTO pizza(id,name,pastatype,prixbase) VALUES (2,'savoyarde','creme',5);
INSERT INTO pizza(id,name,pastatype,prixbase) VALUES (3,'test','test',8);
INSERT INTO pizza(id,name,pastatype,prixbase) VALUES (9,'parmegianno','creme',4);

INSERT INTO composition(idpizza,idingredient) VALUES (1,2);
INSERT INTO composition(idpizza,idingredient) VALUES (1,5);
INSERT INTO composition(idpizza,idingredient) VALUES (2,1);
INSERT INTO composition(idpizza,idingredient) VALUES (2,7);
INSERT INTO composition(idpizza,idingredient) VALUES (1,9);
INSERT INTO composition(idpizza,idingredient) VALUES (9,1);
INSERT INTO composition(idpizza,idingredient) VALUES (9,2);
INSERT INTO composition(idpizza,idingredient) VALUES (9,3);



INSERT INTO users(user_id,login,pwd) VALUES (1,'jean','jean');
INSERT INTO users(user_id,login,pwd) VALUES (2,'luc','luc');

insert into commandes values
(1,1,Date(now())),
(3,2,Date(now()));


insert into consommation values
(1,1),
(1,2),
(3,1);


