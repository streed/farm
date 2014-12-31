#include<EEPROM.h>

/*
 * POC for water sensor prototype
 * 
 * Features:
 * - Write sensor values to EEPROM every 5 minutes
 * - Dump EEPROM data
 * - Diagnostic mode for checking things
 * - Sensor is not energized except when reading
 * 
 * Notes:
 * - To initialize an arduino, start with pin 7 jumpered to ground, which will zero all relevant eeprom vals.
 * 
 * 'Features':
 * - Values are written as an 8-bit number, which loses us 3/4 of our ADC resolution
 * - We can only store 255 values due to the way position is stored, which limits us to 21 hours and 15 minutes of data points
 * - Nothing is interrupt driven, so you may have to wait up to 5 minutes for your data dump to begin =D
 * 
 * Pin Assignments:
 * - Analog 0: Sensor Value
 * - Digital 2: Sensor power
 * - Digital 3: Sensor power
 * - Digital 7: Pull low for data dump
 * - Digital 8: Pull low for diagnostic output
 * 
 * Circuit: 
 * Set up a basic voltage divider with D2 as power, D3 as ground, and A0 reading the divided voltage.
 * D2/D3 can source/sink 40ma, which may or may not be sufficient for our needs. 
 * 330 ohm seems to be a decent value for the divider resistor for proto 1A. 
 * 
 * Future work: 
 * - Remove 'featues'
 * - Set up circuit so that D2 and D3 can alternate as power and ground to lessen electrolytic effects
 * - Get ioref set up so that we get the full ADC range on the sensor
 */

unsigned int pos;
unsigned int dumped = 0;
unsigned int power = 2;
unsigned int ground = 3;

const unsigned int POS_ADDR = 1023;
const unsigned int OVERFLOW_ADDR = 1022;
const unsigned int MAX_ADDR = 255; // TODO: fix pos storage so we can store more data

void setup() {
  // Sensor power I/O
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  digitalWrite(2, LOW);
  digitalWrite(3, LOW);
  
  // Diagnostic switch
  pinMode(8, INPUT_PULLUP);
  
  // Dump switch
  pinMode(7, INPUT_PULLUP);
  
  pos = EEPROM.read(POS_ADDR);
  
  Serial.begin(115200);
}

void swap(unsigned int& a, unsigned int &b) {
  unsigned int t = a;
  a = b;
  b = t;
}

void diag() {
  digitalWrite(power, HIGH);
  delay(500); //settle time
  int sensorValue = analogRead(A0);
  if(power == 3) {
    sensorValue = 1020 - sensorValue;
  }
  Serial.println(sensorValue);
  digitalWrite(power, LOW);
  swap(power, ground);
  delay(500);
}

void dump() {
  Serial.println("--- Dumping stored data ---");
  
  int overflows = EEPROM.read(OVERFLOW_ADDR);
  Serial.print("Overflows: ");
  Serial.println(overflows);
  
  if(overflows) {
    for(unsigned int i = pos; i <= MAX_ADDR; i++) {
      Serial.print(i);
      Serial.print(" : ");
      Serial.println(EEPROM.read(i));
    }
  }
  
  for(unsigned int i = 0; i < pos; i++) {
    Serial.print(i);
    Serial.print(" : ");
    Serial.println(EEPROM.read(i));
  }
  
  Serial.println("--- Dump completed ---");
  
  EEPROM.write(OVERFLOW_ADDR, 0);
  EEPROM.write(POS_ADDR, 0);
  pos = 0;
  
  dumped = 1;
  delay(1);
}

void store() {
  digitalWrite(2, HIGH); // TODO: alternate between 2 and 3 HIGH
  delay(2000); // TODO: empirically verify necessary settle time

  int val = analogRead(A0);
  // Clamp val if it exceeds the set range
  if(val > 255) {
    val = 255;
  }
    
  digitalWrite(2, LOW);

  // Write data
  EEPROM.write(pos, val);
  Serial.println(val);
  
  pos++;
  // Track overflows in the last EEPROM slot
  if(pos > MAX_ADDR) {
    int overflows = EEPROM.read(OVERFLOW_ADDR);
    EEPROM.write(pos, overflows + 1);
    pos = 0;
  }
  
  // Store position
  EEPROM.write(POS_ADDR, pos);

  // Five minutes between reads
  delay(5 * 60 * 1000);
}

void loop() {
  if(!digitalRead(8)) {
    diag();
  }
  else if(!digitalRead(7)) {
    if(!dumped) {
      dump();
    }
  }
  else {
    store();
    dumped = 0;
  }
}

