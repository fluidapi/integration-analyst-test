using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.Results;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace IntegrationTest_Fluid.Controllers
{
    [ApiController]
    [Route("covid/analytic")]
    public class AnalyticCovidCasesController : Controller
    {
        private readonly IQueryHandler<GetAnalyticCovidCasesFilter, IEnumerable<GetAnalyticCovidCasesResult>> _getAnalyticCovidQueryHandler;

        public AnalyticCovidCasesController(IQueryHandler<GetAnalyticCovidCasesFilter, IEnumerable<GetAnalyticCovidCasesResult>> getAnalyticCovidQueryHandler)
        {
            _getAnalyticCovidQueryHandler = getAnalyticCovidQueryHandler;
        }

        public IActionResult Index([FromQuery] GetAnalyticCovidCasesFilter filter)
        {
            var data = _getAnalyticCovidQueryHandler.Handle(filter);

            return Ok(data);
        }
    }
}
