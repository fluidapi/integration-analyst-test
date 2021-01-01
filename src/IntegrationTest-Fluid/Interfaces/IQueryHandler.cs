namespace IntegrationTest_Fluid.Interfaces
{
    public interface IQueryHandler<TQuery, TResult> where TQuery : IQuery
    {
        TResult Handle(TQuery query);
    }
}
