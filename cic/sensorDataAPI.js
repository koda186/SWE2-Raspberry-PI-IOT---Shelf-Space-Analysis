const express = require('express');
const router = express.Router();
const sensorData = require('../../models/sensorDataSchema');




// Routing us to whatever route is in our URL
// This string goes to (GW) Cruz....
// https://team11.softwarengineeringii.com/api/heartbeatsAPI/createNewHeartbeat
router.post('/createNewSensorData', (req, res) => {
    //Create new sensor data with the following variables 
    console.log(req);
    const newSensorData = new sensorData({
        SHELFNUMBER: req.body.SHELFNUMBER,
        TIMESTAMP: req.body.TIMESTAMP,
        SHELFWEIGHT: req.body.SHELFWEIGHT
    });
    newSensorData.save()
        .then(data => {
            res.status(200).json({ msg:`Sensor data was added to the DB ${data}`});
            console.log('Sensor data added sucessccfully to DB. Sensor Data -> ' + data)
        })
        .catch(err => {
            res.status(400).json({
                msg: `Error POST request to /createNewSensorData`,
            });
        });
});

module.exports = router;


// Gets all heartbeats
// URL https://team12.softwareengineeringii.com/api/heartbeat
// returns an array with json heartbeats objects.
// Error -> If not found response status code = 404 and a json object containing message under message key.
// Or if status code is 500 = error ocurred during the query.
router.get('/', (req, res) => {
    sensorData.find()
        .exec()
        .then(docs =>{
            console.log('Documents from Db are ' + docs);
            if (docs.length > 0) {
                res.status(200).json(docs);
            } else {
                res.status(404).json({message: 'Database empty'});
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});



// Retrieve sensor data when requested 
router.post('/:get_sensorData', (req, res) => {

    const SHELFNUMBER = req.body.SHELFNUMBER;
    console.log(SHELFNUMBER);
    sensorData.find().where({SHELFNUMBER: SHELFNUMBER})
        .exec()
        .then(doc => {
            console.log("Sensor Data: " + doc.length + " -> " , doc);
            if (doc.length > 0) {
                res.status(200).json(doc);

            } else {
                res.status(404).json({message: 'Sorry. We could not find a Sensor Data in the database for' + gateway});
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({ error: err});
        });
});