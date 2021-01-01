using IntegrationTest_Fluid.Data;
using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.Results;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrationTest_Fluid.QueryHandlers
{
    public class GetSyntheticCovidCasesQueryHandler : IQueryHandler<GetSyntheticCovidCasesFilter, IEnumerable<GetSyntheticCovidCasesResult>>
    {
        private readonly ICsvContext _csvContext;

        public GetSyntheticCovidCasesQueryHandler(ICsvContext csvContext)
        {
            _csvContext = csvContext;
        }

        public IEnumerable<GetSyntheticCovidCasesResult> Handle(GetSyntheticCovidCasesFilter filter)
        {
            var data = _csvContext.GetCsvData<CovidData>("Resources/input.csv");

            var result = data
                .Select(x => new
                {
                    month = Convert.ToDateTime(x.date).ToString("yyyy-MM"),
                    x.cases,
                    x.deaths
                })
                .GroupBy(x => new
                {
                    x.month
                })
                .Select(x => new GetSyntheticCovidCasesResult
                {
                    month = x.Key.month,
                    cases = x.Sum(y => y.cases),
                    deaths = x.Sum(y => y.deaths)
                });

            if(!string.IsNullOrEmpty(filter?.month))
            {
                result = result.Where(x => x.month == filter.month);
            }

            return result;
        }
    }
}
