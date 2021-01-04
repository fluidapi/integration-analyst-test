using IntegrationTest_Fluid.Interfaces;

namespace IntegrationTest_Fluid.Queries
{
    public class GetSyntheticCovidCasesFilter : IQuery
    {
        public string month { get; set; }
    }
}
