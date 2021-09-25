const mongoose = require('mongoose'); 

// This is our mongoose schema that we can send and retrieve data from db
const sensorDataSchema = mongoose.Schema({
    SHELFNUMBER: String,
	TIMESTAMP: {type: Date, default: Date.now},
    SHELFWEIGHT: String
});

// This is where the file is located - it is a pointer to our data that we are modeling in our DB
module.exports = mongoose.model('sensorData', sensorDataSchema);

