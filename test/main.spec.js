const chai = require('chai');
const subSet = require('chai-subset');
const main = require('../src/main');

chai.use(subSet);

const covidSchema = {
    month: month => month,
    cases: cases => cases,
    deaths: deaths => deaths
};

describe('Teste das funcoes', () => {

    it('generateJsonOutput', () => {
        main.generateJsonOutput(
            process.cwd() + '/data/input.csv',
            { delimiter: ',' },
            function (val) {
                console.log(val);
                chai.expect(val.length).to.be.equals(3);
                chai.expect(val).to.containSubset([covidSchema]);
            }
        );
    });
});