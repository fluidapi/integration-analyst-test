using IntegrationTest_Fluid.Interfaces;

namespace IntegrationTest_Fluid.Queries
{
    public class GetAnalyticCovidCasesFilter : IQuery
    {
        public string uf { get; set; }
        public int month { get; set; }
        public int year { get; set; }
    }
}
