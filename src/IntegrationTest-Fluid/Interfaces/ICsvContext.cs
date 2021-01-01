using System;
using System.Collections.Generic;

namespace IntegrationTest_Fluid.Interfaces
{
    public interface ICsvContext 
    {
        public IEnumerable<TResult> GetCsvData<TResult>(string file);
    }
}
