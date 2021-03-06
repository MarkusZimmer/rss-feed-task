CREATE TABLE `RSS_ANALYTICS` (
    id NUMBER AUTO_INCREMENT,
    PRIMARY KEY(id)
);

CREATE TABLE `WORD` (
    id NUMBER AUTO_INCREMENT,
    keyword VARCHAR NOT NULL,
    amount INT,
    RSS_ANALYTICS_ID NUMBER,
    PRIMARY KEY(id),
    FOREIGN KEY(RSS_ANALYTICS_ID) REFERENCES `RSS_ANALYTICS`(id)
);

CREATE TABLE `NEWS` (
    id NUMBER AUTO_INCREMENT,
    title VARCHAR NOT NULL,
    link VARCHAR NOT NULL,
    description TEXT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE `WORD_NEWS` (
    id NUMBER AUTO_INCREMENT,
    word_id NUMBER,
    news_id NUMBER,
    PRIMARY KEY(id),
    FOREIGN KEY(word_id) REFERENCES `Word`(id),
    FOREIGN KEY(news_id) REFERENCES `News`(id)
);


