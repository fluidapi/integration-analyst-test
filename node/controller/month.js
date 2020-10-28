module.exports = groupByMonth

function groupByMonth (array, key){
    return array.reduce((result, currentValue) => {
        (result[currentValue[key]] = result[currentValue[key]] || []).push(currentValue)
        return result
    }, {})
};