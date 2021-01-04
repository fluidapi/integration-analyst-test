using IntegrationTest_Fluid.Infraestructure;
using IntegrationTest_Fluid.Interfaces;
using IntegrationTest_Fluid.Queries;
using IntegrationTest_Fluid.QueryHandlers;
using IntegrationTest_Fluid.Results;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System.Collections.Generic;

namespace IntegrationTest_Fluid
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers();
            services.AddScoped<ICsvContext, CsvContext>();
            services.AddScoped<IQueryHandler<GetAnalyticCovidCasesFilter, IEnumerable<GetAnalyticCovidCasesResult>>, GetAnalyticCovidCasesQueryHandler>();
            services.AddScoped<IQueryHandler<GetSyntheticCovidCasesFilter, IEnumerable<GetSyntheticCovidCasesResult>>, GetSyntheticCovidCasesQueryHandler>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
