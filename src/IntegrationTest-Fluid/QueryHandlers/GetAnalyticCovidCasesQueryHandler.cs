using IntegrationTest_Fluid.Data;
using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.Results;
using System;
using System.Collections.Generic;
using System.Linq;

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

            var result = data
                .Select(x => new GetAnalyticCovidCasesResult
                {
                    Cases = x.Cases,
                    Date = x.Date,
                    Deaths = x.Deaths,
                    Uf = x.Uf
                });

            if (!string.IsNullOrEmpty(filter?.month))
            {
                result = result
                    .Where(x => x.Date.ToString("yyyy-MM") == filter.month);
            }
            if (!string.IsNullOrEmpty(filter?.uf))
            {
                result = result
                    .Where(x => x.Uf == filter.uf);
            }

            return result;
        }
    }
}
