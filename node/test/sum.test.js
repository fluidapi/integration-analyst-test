const sumTest = require('../controller/sum')

test("Efetua a soma a partir da chave do objeto", () => {
  const input = {"2020-01": [ { "month": "2020-01", "cases": 10, "deaths": 0 }, { "month": "2020-01", "cases": 20, "deaths": 1 }]};

  const output = sumTest(input["2020-01"], "cases");

  const expected = 30;

  expect(output).toBe(expected);
});

test("Efetua a soma a partir da chave do objeto", () => {
  const input = {"2020-01": [ { "month": "2020-01", "cases": 0, "deaths": 5 }, { "month": "2020-01", "cases": 2, "deaths": 10 }]};

  const output = sumTest(input["2020-01"], "deaths");

  const expected = 15;

  expect(output).toBe(expected);
});