using IntegrationTest_Fluid.Interfaces;

namespace IntegrationTest_Fluid.Queries
{
    public class GetAnalyticCovidCasesFilter : IQuery
    {
        public string month { get; set; }
        public string uf { get; set; }
    }
}
