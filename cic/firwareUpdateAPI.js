const express = require('express');
const router = express.Router();
const firmwareUpdate = require('../../models/firmwareUpdateSchema');


// Routing us to whatever route is in our URL
// https://team11.softwarengineeringii.com/api/firmwareUpdateAPI/createNewFirmwareUpdate
router.post('/createNewFirmwareUpdate', (req, res) => {
    //Create new software/firmware update with the following variables 
    console.log(req);
    const newFirmwareUpdate = new firmwareUpdate({
        UPDATEFIRMWAREREQUEST: req.body.software,
        TIMESTAMP: req.body.TIMESTAMP,
        // NOTE FROM IBRAHIM. 
        // changing below because status is not created initially from front end. 
        // it will be updated by GWC
        //UPDATEDFIRMWARESTATUS: "To be updated"
         UPDATEDFIRMWARESTATUS: req.body.UPDATEDFIRMWARESTATUS

    });


    newFirmwareUpdate.save()
    .then(data => {
        res.status(200).json({ msg:`Thank you. Your updates have been initiated. You will be able to check the status soon.`});
        console.log('Update added sucessccfully to DB. F=irmware update -> ' + data)
    })
    .catch(err => {
            res.status(400).json({
                msg: `Error POST request to /createNewFirmwareUpdate`,
            });
        });
});

module.exports = router;

// Retrieve all status updates for update requested
router.post('/:get_software_status', (req, res) => {
    const update = req.body.update;
    console.log(update);
    firmwareUpdate.find().where({UPDATEFIRMWAREREQUEST: update})
        .exec()
        .then(doc => {
            console.log("Status: " + doc.length + " -> " , doc);
            if (doc.length > 0) {
                res.status(200).json(doc);

            } else {
                res.status(404).json({message: 'Sorry. We could not find a status for ' + update});
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({ error: err});
        });
});