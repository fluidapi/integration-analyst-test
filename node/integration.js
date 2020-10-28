module.exports = integration
const month = require('./controller/month')
const casesDeaths = require('./controller/casesDeaths')

const fs = require('fs')
const readable = fs.createReadStream('./data/input.csv')

const csv = require("csvtojson");

async function integration() {
    return csv()
        .fromStream(readable)
        .then((jsonObj) => {
            try {
                json = jsonObj.map((i) => {
                    return {
                        month: i.date.substring(0, 7),
                        cases: parseInt(i.cases),
                        deaths: parseInt(i.deaths)
                    }
                })
                const groupedMonth = month(json, "month")
                const groupedResult = casesDeaths(groupedMonth, Object.keys(groupedMonth))
                fs.appendFile('./data/output-result.json', JSON.stringify(groupedResult), function (err) {
                    if (err) throw err;
                    console.log('\nFile saved in /data folder...')
                })
                console.log(groupedResult)
                return groupedResult
            } catch (error) {
                console.log(error)
            }
        })
}


const action = integration()

