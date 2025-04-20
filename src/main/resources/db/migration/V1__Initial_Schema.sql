CREATE TABLE bidang_urusan (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    kode_bidang_urusan  varchar(255) UNIQUE NOT NULL,
    nama_bidang_urusan  varchar(255) NOT NULL,
    status              varchar(255) NOT NULL,
    created_date        timestamp NOT NULL,
    last_modified_date  timestamp NOT NULL,
    version             integer NOT NULL
);