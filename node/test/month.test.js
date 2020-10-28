const monthTest = require('../controller/month')

test("Agrupa os meses equivalentes", () => {
  const input = [ { "month": "2020-01", "cases": 0, "deaths": 0 }];

  const output = monthTest(input, "month");

  const expected = {"2020-01": [ { "month": "2020-01", "cases": 0, "deaths": 0 } ]};

  expect(output).toStrictEqual(expected);
});