const express = require('express');
const router = express.Router();
const heartbeat = require('../../models/heartbeatsSchema');


// Routing us to whatever route is in our URL
// This string goes to (GW) Cruz....
// https://team11.softwarengineeringii.com/api/heartbeatsAPI/createNewHeartbeat
router.post('/createNewHeartbeat', (req, res) => {
    //Create new heartbeat with the following variables 
    console.log(req);
    const newHeartbeat = new heartbeat({
        GWID: req.body.GWID,
        TIMESTAMP: Date(),
        TESTTYPE: req.body.TESTTYPE,
        STATUS: req.body.STATUS
    });
    newHeartbeat.save()
    .then(data => {
        res.status(200).json({ msg:`A new heart beat was added to the DB ${data}`});
        console.log('New heartbeat added sucessccfully to DB. Hearbeat -> ' + data)
    })
    .catch(err => {
            res.status(400).json({
                msg: `Error POST request to /createNewHeartbeat`,
            });
        });
});

module.exports = router;
//commented this out for test
//console.log(newHeartbeat);

    //sent status 200 if a new HB has been successfully added to DB
    // res.status(200).json({msg: `New HB Added to DB ${newHeartbeat}`});
    // console.log(req.body);
    // console.log(req);



// Retrieve all heartbeats for gateway requested for
router.post('/:get_heartbeat', (req, res) => {
    const gateway = req.body.gateway;
    console.log(gateway);
    heartbeat.find().where({GWID: gateway})
        .exec()
        .then(doc => {
            console.log("Gateway heartbeat: " + doc.length + " -> " , doc);
            if (doc.length > 0) {
                res.status(200).json(doc);

            } else {
                res.status(404).json({message: 'Sorry. We could not find a heartbeat in the database for' + gateway});
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({ error: err});
        });
});