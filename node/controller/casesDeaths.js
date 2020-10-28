module.exports = groupByCasesAndDeaths

const sum = require('./sum')

function groupByCasesAndDeaths(array, keys){
    return keys.map((key) => {
        return {
            month: key,
            cases: sum(array[key], "cases"),
            deaths: sum(array[key], "deaths")
        }
    })
}