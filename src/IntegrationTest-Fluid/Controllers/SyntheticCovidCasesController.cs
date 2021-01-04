using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.Results;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace IntegrationTest_Fluid.Controllers
{
    [ApiController]
    [Route("covid/synthetic")]
    public class SyntheticCovidCasesController : Controller
    {
        private readonly IQueryHandler<GetSyntheticCovidCasesFilter, IEnumerable<GetSyntheticCovidCasesResult>> _getSyntheticCovidQueryHandler;

        public SyntheticCovidCasesController(IQueryHandler<GetSyntheticCovidCasesFilter, IEnumerable<GetSyntheticCovidCasesResult>> getSyntheticCovidQueryHandler)
        {
            _getSyntheticCovidQueryHandler = getSyntheticCovidQueryHandler;
        }

        public IActionResult Index([FromQuery] GetSyntheticCovidCasesFilter filter)
        {
            var data = _getSyntheticCovidQueryHandler.Handle(filter);

            return Ok(data);
        }
    }
}
