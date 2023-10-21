import csv
import pymysql

connection = pymysql.connect(
        host="localhost",
        user="root",
        password="1234",
        port=3306,
        database="tech_labs",
        charset='utf8mb4'
    )

create_product_table_query = """
CREATE TABLE IF NOT EXISTS product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_image VARCHAR(255) NOT NULL,
    product_url VARCHAR(255) NOT NULL,
    original_price INT NOT NULL,
    sale_price INT NOT NULL,
    created_date DATETIME,
    modified_date DATETIME
);
"""

create_relative_product_table_query = """
CREATE TABLE relative_product (
    relative_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_product_id BIGINT NOT NULL,
    result_product_id BIGINT NOT NULL,
    score INT NOT NULL,
    created_date DATETIME,
    modified_date DATETIME,
    FOREIGN KEY (target_product_id) REFERENCES product(product_id),
    FOREIGN KEY (result_product_id) REFERENCES product(product_id)
);
"""

insert_product_table_query = """
    INSERT INTO product (
        product_id,
        product_name, 
        product_image, 
        product_url, 
        original_price, 
        sale_price,
        created_date,
        modified_date
    ) VALUES (%s,%s, %s, %s, %s, %s, NOW(), NOW())
"""

insert_relative_product_table_query = """
    INSERT INTO relative_product (
        target_product_id, 
        result_product_id, 
        score,
        created_date,
        modified_date
    ) VALUES (%s, %s, %s, NOW(), NOW())
"""

def create_table(query) :
    try:
        with connection.cursor() as cursor:
            cursor.execute(query)
            connection.commit()
            print("테이블 생성.")
    except Exception as e:
        print(f"테이블 생성 중 오류 : {str(e)}")
    finally:
        cursor.close()
    

def import_csv_to_mysql(csv_file, table_name, query, columnIdxs=None):
    
    try:
        # 커서 생성
        with connection.cursor() as cursor:
            sql = f"SELECT COUNT(*) FROM {table_name}"
            cursor.execute(sql)
            # 결과 가져오기
            result = cursor.fetchone()
            if result[0] > 0:
                return
            # CSV 파일 열기
            with open(csv_file, 'r', encoding='utf-8') as csvfile:
                csv_reader = csv.reader(csvfile)

                # 데이터를 MySQL 테이블에 삽입
                for row in csv_reader:
                    try:
                        if columnIdxs is None:
                            selected_data = tuple(row)
                        else:
                            selected_data = tuple(row[idx] for idx in columnIdxs)
                        cursor.execute(query, selected_data)
                    except Exception as e:
                        print(f"데이터 입력 오류 : {str(e)}")

            # 변경사항 커밋
            connection.commit()

    finally:
        # 연결 종료
        cursor.close()
create_table(create_product_table_query)
create_table(create_relative_product_table_query)
import_csv_to_mysql('product.csv','product',insert_product_table_query)
import_csv_to_mysql('rec.csv','relative_product',insert_relative_product_table_query, columnIdxs=[0,1,2])
connection.close()