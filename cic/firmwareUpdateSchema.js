const mongoose = require('mongoose');

// This is our mongoose schema that we can send and retrieve data from db
const firmwareUpdateSchema = mongoose.Schema({
    UPDATEFIRMWAREREQUEST: String,
	TIMESTAMP: {type: Date, default: Date.now},
	UPDATEDFIRMWARESTATUS: String
});

// This is where the file is located - it is a pointer to our data that we are modeling in our DB
module.exports = mongoose.model('firmwareUpdate', firmwareUpdateSchema);

