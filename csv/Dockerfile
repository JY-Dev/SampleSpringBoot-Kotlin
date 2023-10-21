FROM python:3.7-slim

RUN pip install PyMySQL

COPY product.csv .
COPY rec.csv .
COPY insert_csv.py .

CMD ["python","insert_csv.py"]