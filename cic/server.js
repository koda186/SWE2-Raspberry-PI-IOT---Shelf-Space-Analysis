// Entry point variable that requires express, path and mongoose
const express = require('express');

const path = require('path');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

//------------------------- Loggers --------------------

// Get called every time a request is made 
const logger = require('./middleware/logger.js');

// Init variable app with express 
const app = express();

// Handles url encoded data 
app.use(express.urlencoded({extended: false}));
//---------------------------------------HOMPAGE ROUTE-----------------------------------------------------------------
// Homepage Route that Ibrahim will need
app.use(express.static(path.join(__dirname, 'public')));


//---------------------------------------DATABASE CONNECTION-----------------------------------------------------------------

//..........connection string here.........
var mongoDB = 'mongodb://127.0.0.1:27017/test'; // check the name of the DB in DO and change if needed

mongoose.connect(mongoDB, { useNewUrlParser: true});
    
    //, () => console.log("Connected to DB"));

var db = mongoose.connection;

db.on('error', console.error.bind(console, 'MongoDB connection error: '));


// ---------------------- Set a Static folder ---------------------------------------------------------
// Body Prser Middleware -> Parses the data, Allows us to use raw JSON
app.use(express.json());
// Makes it easier to use raw data on json 

// -------------------------------GW ROUTES------------------------------------------------

// Gateway Heartbeat-API routes
// test using postman: http://localhost:5000/api/heartbeatsAPI/createNewHeartbeat
// /api/heartbeat = the path of the url to call api
app.use('/api/heartbeatsAPI', require('./routes/api/heartbeatsAPI'));

// -----------------------------SENSOR ROUTES--------------------------------------------------
// Sensor Data-API routes
app.use('/api/sensorDataAPI', require('./routes/api/sensorDataAPI'));


// create variable to set to port number using 5000...looking for environment variables called PORT if not avalible run on 5000
const PORT = process.env.PORT || 3000;

// App is listening on a port and creating console variable that lets us know when server is runner
app.listen(PORT, () => console.log(`Server started on port ${PORT}`));
