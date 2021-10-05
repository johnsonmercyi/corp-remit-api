CREATE TABLE businesses (
	business_id				UUID PRIMARY KEY NOT NULL,
	name					VARCHAR(150) UNIQUE NOT NULL,
	rc_no					VARCHAR(20) UNIQUE,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE branches (
	branch_id				UUID PRIMARY KEY NOT NULL,
	name					VARCHAR(150) UNIQUE NOT NULL,
	address					VARCHAR(250) NOT NULL,
	business_id				UUID REFERENCES businesses (business_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE departments (
	department_id			UUID PRIMARY KEY NOT NULL,
	name					VARCHAR(150) UNIQUE NOT NULL,
	branch_id				UUID REFERENCES branches (branch_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE roles (
	role_id					UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(150),
	type					VARCHAR(50),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE profiles (
	profile_id				UUID PRIMARY KEY NOT NULL,
	first_name				VARCHAR(150) NOT NULL,
	last_name				VARCHAR(150) NOT NULL,
	gender					VARCHAR(10) NOT NULL,
	dob						DATE,
	nationality				VARCHAR(150),
	country_of_residence	VARCHAR(150),
	city					VARCHAR(150),
	address_1				VARCHAR(250),
	address_2				VARCHAR(250),
	postal_code				VARCHAR(50),
	mobile					VARCHAR(20) UNIQUE,
	avarta					VARCHAR(1000),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE users (
	user_id					UUID PRIMARY KEY NOT NULL,
	profile_id				UUID REFERENCES profiles (profile_id) NOT NULL,
	username				VARCHAR(50) UNIQUE NOT NULL,
	password				TEXT NOT NULL,
	email					VARCHAR(50) UNIQUE NOT NULL,
	remember_me_token		VARCHAR(150),
	role_id					UUID REFERENCES roles (role_id),-- role_id isn't filled on user registration. The admin does the assigning.
	status					int default(1) NOT NULL,
	last_login				TIMESTAMP,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE staff (
	staff_id				UUID PRIMARY KEY NOT NULL,
	profile_id				UUID REFERENCES profiles (profile_id) NOT NULL,
	branch_id				UUID REFERENCES branches (branch_id) NOT NULL,
	user_id					UUID REFERENCES users (user_id),
	role_id					UUID REFERENCES roles (role_id) NOT NULL,
	department_id			UUID REFERENCES departments (department_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE privileges (
	privilege_id			UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(250),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE user_privileges (
	user_privilege_id		UUID PRIMARY KEY NOT NULL,
	user_id					UUID REFERENCES users (user_id) NOT NULL,
	privilege_id			UUID REFERENCES privileges (privilege_id) NOT NULL,
	revoked					BOOLEAN,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE categories (
	category_id				UUID PRIMARY KEY NOT NULL,
	name					VARCHAR(150) UNIQUE NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE products (
	product_id				UUID PRIMARY KEY NOT NULL,
	category_id				UUID REFERENCES categories (category_id) NOT NULL,
	title					VARCHAR(150) UNIQUE NOT NULL,
	"desc"					VARCHAR(250),
	meta					TEXT,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE product_prices (
	prices_id				UUID PRIMARY KEY NOT NULL,
	product_id				UUID REFERENCES products (product_id) NOT NULL,
	cost_price				FLOAT NOT NULL,
	sales_price				FLOAT NOT NULL,
	list_price				FLOAT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE suppliers (
	supplier_id				UUID PRIMARY KEY NOT NULL,
	profile_id				UUID REFERENCES profiles (profile_id),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE customers (
	customer_id				UUID PRIMARY KEY NOT NULL,
	profile_id				UUID REFERENCES profiles (profile_id),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE beneficiary_types (
	type_id					UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(150) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE beneficiaries (
	beneficiary_id			UUID PRIMARY KEY NOT NULL,
	profile_id				UUID REFERENCES profiles (profile_id) NOT NULL,
	type_id					UUID REFERENCES beneficiary_types (type_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE revenue_flow_mediums (
	medium_id				UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(150) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE batches (
	batch_id				UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(50) NOT NULL, -- PURCHASE, ORDER, EXPENSES etc
	user_id					UUID REFERENCES users (user_id) NOT NULL,
	status					INT, -- 1 (open) OR 0 (closed)
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE accounts (
	account_id				UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(100) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE statuses (
	status_id				UUID PRIMARY KEY NOT NULL,
	"desc"					VARCHAR(50),
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE transactions (
	transaction_id			UUID PRIMARY KEY NOT NULL,
	account_id				UUID REFERENCES accounts (account_id) NOT NULL,
	batch_id				UUID REFERENCES batches (batch_id) NOT NULL,
	branch_id				UUID REFERENCES branches (branch_id) NOT NULL,
	total_amount			FLOAT NOT NULL,
	balance_due				FLOAT NOT NULL,
	status_id				UUID REFERENCES statuses (status_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE revenues (
	revenue_id				UUID PRIMARY KEY NOT NULL,
	transaction_id			UUID REFERENCES transactions (transaction_id) NOT NULL,
	amount					FLOAT NOT NULL,
	balance					FLOAT NOT NULL,
	medium_id				UUID REFERENCES revenue_flow_mediums (medium_id) NOT NULL,
	user_id					UUID REFERENCES users (user_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE expenses (
	expenses_id				UUID PRIMARY KEY NOT NULL,
	batch_id				UUID REFERENCES batches (batch_id) NOT NULL,
	amount					FLOAT NOT NULL,
	beneficiary_id			UUID REFERENCES beneficiaries (beneficiary_id) NOT NULL,
	medium_id				UUID REFERENCES revenue_flow_mediums (medium_id) NOT NULL,
	account_id				UUID REFERENCES accounts (account_id) NOT NULL,
	"desc"					TEXT,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP	
);

CREATE TABLE purchases (
	purchase_id				UUID PRIMARY KEY NOT NULL,
	product_id				UUID REFERENCES products (product_id) NOT NULL,
	batch_id				UUID REFERENCES batches (batch_id) NOT NULL,
	supplier_id				UUID REFERENCES suppliers (supplier_id),
	total_purchase			INT NOT NULL,
	total_price				FLOAT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE orders (
	order_id				UUID PRIMARY KEY NOT NULL,
	product_id				UUID REFERENCES products (product_id) NOT NULL,
	batch_id				UUID REFERENCES batches (batch_id) NOT NULL,
	customer_id				UUID REFERENCES suppliers (supplier_id),
	total_sale				INT NOT NULL,
	sold_price_per_item		FLOAT NOT NULL,
	total_price				FLOAT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE restock (
	restock_id				UUID PRIMARY KEY NOT NULL,
	product_id				UUID REFERENCES products (product_id) NOT NULL,
	batch_id				UUID REFERENCES batches (batch_id) NOT NULL,
	channel_id				UUID REFERENCES accounts (account_id) NOT NULL,
	purchase_id				UUID REFERENCES purchases (purchase_id),
	balance_before			INT NOT NULL,
	total_restock			INT NOT NULL,
	balance_after			INT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE stocks (
	stock_id				UUID PRIMARY KEY NOT NULL,
	product_id				UUID REFERENCES products (product_id) NOT NULL,
	total_product			INT NOT NULL,
	total_out				INT NOT NULL,
	available				INT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE stock_balances (
	stock_balance_id		UUID PRIMARY KEY NOT NULL,
	stock_id				UUID  REFERENCES stocks (stock_id) NOT NULL,
	total_product			INT NOT NULL,
	total_out				INT NOT NULL,
	balance					INT NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);

CREATE TABLE returned_sales (
	returned_sale_id		UUID PRIMARY KEY NOT NULL,
	ref_transaction_id		UUID REFERENCES transactions (transaction_id) NOT NULL,
	created_at				TIMESTAMP,
	updated_at				TIMESTAMP
);