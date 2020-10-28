const casesDeathsTest = require('../controller/casesDeaths')

test("Agrupa os casos para o mês", () => {
  const input = {"2020-01": [ { "month": "2020-01", "cases": 0, "deaths": 0 } ]};

  const output = casesDeathsTest(input, Object.keys(input));

  const expected = [ { "month": "2020-01", "cases": 0, "deaths": 0 } ];

  expect(output).toStrictEqual(expected);
});