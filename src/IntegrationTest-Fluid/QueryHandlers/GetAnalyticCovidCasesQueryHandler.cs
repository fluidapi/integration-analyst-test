using IntegrationTest_Fluid.Data;
using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.Results;
using System;
using System.Collections.Generic;

namespace IntegrationTest_Fluid.QueryHandlers
{
    public class GetAnalyticCovidCasesQueryHandler : IQueryHandler<GetAnalyticCovidCasesFilter, IEnumerable<GetAnalyticCovidCasesResult>>
    {
        private readonly ICsvContext _csvContext;

        public GetAnalyticCovidCasesQueryHandler(ICsvContext csvContext)
        {
            _csvContext = csvContext;
        }

        public IEnumerable<GetAnalyticCovidCasesResult> Handle(GetAnalyticCovidCasesFilter filter)
        {
            var data = _csvContext.GetCsvData<CovidData>("Resources/input.csv");

            foreach (var item in data)
            {
                yield return new GetAnalyticCovidCasesResult
                {
                    cases = Convert.ToInt32(item.cases),
                    date = item.date,
                    uf = item.uf,
                    deaths = Convert.ToInt32(item.deaths),
                    time = item.time
                };
            }
        }
    }
}
