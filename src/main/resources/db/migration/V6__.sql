ALTER TABLE `role`
    ADD deleted BIT(1) NULL;

ALTER TABLE token
    ADD deleted BIT(1) NULL;

ALTER TABLE user
    ADD deleted BIT(1) NULL;