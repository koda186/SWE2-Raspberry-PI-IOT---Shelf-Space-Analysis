const mongoose = require('mongoose'); 

// This is our mongoose schema that we can send and retrieve data from db
const heartbeatsSchema = mongoose.Schema({
    GWID: Number,
	TIMESTAMP: {type: Date, default: Date.now},
	TESTTYPE: String,
	STATUS: String
});

// This is where the file is located - it is a pointer to our data that we are modeling in our DB
module.exports = mongoose.model('heartbeat', heartbeatsSchema);

