FROM python:3.9

WORKDIR /app

COPY detect_licencePlate.py .

# Install the required dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Run the Python script when the container starts
CMD ["python", "detect_licencePlate.py"]
