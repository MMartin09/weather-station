#include <Arduino_MKRENV.h>

String temp_sensor_id    = "f7a0d9cc-6f73-4090-9d1d-e8694f6c4c2c";
String hum_sensor_id     = "35958ba9-7447-4756-9b6e-700521a80a88";
String ambient_sensor_id = "74722156-26ef-4b5c-a308-461dbd473bc0";

void setup() {
  Serial.begin(9600);
  while (!Serial) ;

  if (!ENV.begin()) {
    Serial.println("Failed to initialize MKR ENV shield!");
    while (1);
  }
}

void loop() {
  String msg_tmp = temp_sensor_id + "=" + ENV.readTemperature();
  Serial.print(msg_tmp);

  String msg_hum = hum_sensor_id + "=" + ENV.readHumidity();
  Serial.print(msg_hum);

  String msg_amd = ambient_sensor_id + "=" + ENV.readIlluminance();
  Serial.print(msg_amd);

  delay(10000);
}
