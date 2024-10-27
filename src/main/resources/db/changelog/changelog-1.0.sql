-- liquibase formatted sql

-- changeset leon:change1-1
CREATE TABLE "configuration_codes" ("configuration_id" UUID NOT NULL, "codes_id" UUID NOT NULL, CONSTRAINT "configuration_codes_pkey" PRIMARY KEY ("configuration_id", "codes_id"));

-- changeset leon:change1-2
CREATE TABLE "code_words" ("code_id" UUID NOT NULL, "words_id" UUID NOT NULL);

-- changeset leon:change1-3
CREATE TABLE "solution_bugs" ("solution_id" UUID NOT NULL, "bugs_id" UUID NOT NULL, CONSTRAINT "solution_bugs_pkey" PRIMARY KEY ("solution_id", "bugs_id"));

-- changeset leon:change1-4
ALTER TABLE "configuration_codes" ADD CONSTRAINT "uk_ojb48o72tahwues3uvlrsm3i9" UNIQUE ("codes_id");

-- changeset leon:change1-5
ALTER TABLE "code_words" ADD CONSTRAINT "uk_rnftyyvtm3o21whfbxb8ys7dt" UNIQUE ("words_id");

-- changeset leon:change1-6
ALTER TABLE "solution_bugs" ADD CONSTRAINT "uk_t6949bp8xqli9wp3yge2ki1g0" UNIQUE ("bugs_id");

-- changeset leon:change1-7
CREATE TABLE "bug" ("id" UUID NOT NULL, "correct_value" VARCHAR(255), "error_type" INTEGER, "word_id" UUID, CONSTRAINT "bug_pkey" PRIMARY KEY ("id"));

-- changeset leon:change1-8
CREATE TABLE "code" ("id" UUID NOT NULL, CONSTRAINT "code_pkey" PRIMARY KEY ("id"));

-- changeset leon:change1-9
CREATE TABLE "configuration" ("id" UUID NOT NULL, CONSTRAINT "configuration_pkey" PRIMARY KEY ("id"));

-- changeset leon:change1-10
CREATE TABLE "solution" ("id" UUID NOT NULL, "code_id" UUID, CONSTRAINT "solution_pkey" PRIMARY KEY ("id"));

-- changeset leon:change1-11
CREATE TABLE "word" ("id" UUID NOT NULL, "word" VARCHAR(255), CONSTRAINT "word_pkey" PRIMARY KEY ("id"));

-- changeset leon:change1-12
ALTER TABLE "solution" ADD CONSTRAINT "fk20ltujstgdyb0ecmkjg2r9hyp" FOREIGN KEY ("code_id") REFERENCES "code" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-13
ALTER TABLE "configuration_codes" ADD CONSTRAINT "fk2ukcuug7ve2764uau9s6ow7kh" FOREIGN KEY ("codes_id") REFERENCES "code" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-14
ALTER TABLE "bug" ADD CONSTRAINT "fkdkqevhvo9ou92q6bvqrtbgt1h" FOREIGN KEY ("word_id") REFERENCES "word" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-15
ALTER TABLE "configuration_codes" ADD CONSTRAINT "fkeu1jbi9ygfualavyrmban6frf" FOREIGN KEY ("configuration_id") REFERENCES "configuration" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-16
ALTER TABLE "solution_bugs" ADD CONSTRAINT "fki2bngjltgjgspthdfr950lqx" FOREIGN KEY ("solution_id") REFERENCES "solution" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-17
ALTER TABLE "code_words" ADD CONSTRAINT "fkju0l4dvy0qamwtdj5mipxt9x" FOREIGN KEY ("words_id") REFERENCES "word" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-18
ALTER TABLE "code_words" ADD CONSTRAINT "fkkix1vyriqpnnll47eei8s452v" FOREIGN KEY ("code_id") REFERENCES "code" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset leon:change1-19
ALTER TABLE "solution_bugs" ADD CONSTRAINT "fko8ankki1ifj8fvd1s64i0qvip" FOREIGN KEY ("bugs_id") REFERENCES "bug" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

